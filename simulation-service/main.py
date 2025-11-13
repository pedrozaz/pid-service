import uvicorn
from fastapi import FastAPI
from typing import List

from models import SimulationRequest, SimulationResponse, TimePoint
from simulation import run_fopdt_simulation

app = FastAPI(
    title="PID Simulation Service",
    description="A service for simulate response from PID control systems"
)

@app.post("/simulate", response_model=SimulationResponse)
async def run_simulation(request : SimulationRequest):
    print(f"[API] Received simulation with plant gain: {request.plant.gain}")
    print(f"[API] PID gain: Kp={request.pid.kp}, Ki={request.pid.ki}, Kd={request.pid.kd}")

    simulation_data = run_fopdt_simulation(
        plant = request.plant,
        pid = request.pid,
        setpoint = request.setpoint,
        duration = request.duration,
        time_step = request.time_step,
    )

    results = [
        TimePoint(time=t, process_variable=pv, setpoint=sp)
        for t, pv, sp in simulation_data
    ]

    return SimulationResponse(results = results)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8001)