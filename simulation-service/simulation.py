import numpy as np
from scipy.integrate import solve_ivp
from collections import deque
from models import PlantModel, PIDParams

class PIDController:
    def __init__(self, kp: float, ki: float, kd: float, time_step: float):
        self.kp = kp
        self.ki = ki
        self.kd = kd
        self.time_step = time_step

        self.integral_error = 0.0
        self.previous_error = 0.0

    def update(self, setpoint: float, process_variable: float) -> float:
        error = setpoint - process_variable
        p_term = self.kp * error

        self.integral_error += error * self.time_step
        i_term = self.ki * self.integral_error

        derivative_error = (error - self.previous_error) / self.time_step
        d_term = self.kd * derivative_error

        self.previous_error = error

        return p_term + i_term + d_term

def run_fopdt_simulation(plant: PlantModel, pid: PIDParams, setpoint: float, duration: float, time_step: float):
    controller = PIDController(pid.kp, pid.ki, pid.kd, time_step)
    K = plant.gain
    tau = plant.time_constant
    theta = plant.dead_time

    dead_time_steps = int(theta / time_step)
    control_buffer = deque([0.0] * dead_time_steps, maxlen=dead_time_steps + 1)

    # dy/dt = (K * u(t) - y(t)) / tau
    def first_order_system(t, y, u_delayed):
        process_variable = y[0]
        dydt = (K * u_delayed - process_variable) / tau
        return [dydt]

    num_steps = int(duration / time_step)
    times = np.linspace(0, duration, num_steps)

    current_state = [0.0]

    results = []

    print (f"[Sim] Initializing simulation. Steps: {num_steps}, Step delay: {dead_time_steps}")

    for t_start in times:
        current_pv = current_state[0]
        results.append((t_start, current_pv, setpoint))

        control_signal = controller.update(setpoint, current_pv)
        control_buffer.append(control_signal)

        u_delayed = control_buffer[0]

        t_end = t_start + time_step
        sol = solve_ivp(
            fun = first_order_system,
            t_span = [t_start, t_end],
            y0 = current_state,
            method = 'RK45',
            args = (u_delayed, ),
        )

        current_state = sol.y[:, -1]

    print("[Sim] Simulation complete.")
    return results
