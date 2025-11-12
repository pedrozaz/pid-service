import uvicorn
from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

class PlantModel(BaseModel):
    gain: float
    time_constant: float
    dead_time: float

class PIDParams(BaseModel):
    kp: float
    ki: float
    kd: float

class SimulationRequest(BaseModel):
    plant: PlantModel
    pid: PIDParams
    setpoint: float
    duration: float
    time_step: float = 0.1

class TimePoint(BaseModel):
    time: float
    process_variable: float
    setpoint: float

class SimulationResponse(BaseModel):
    results: List[TimePoint]

app = FastAPI(
    title="PID Simulation Service",
    description="A service for simulate response from PID control systems"
)

@app.post("/simulate", response_model=SimulationResponse)
async def run_simulation(request: SimulationRequest):
    print(f"[LOG] Received simulation with plant gain: {request.plant.gain}")
    print(f"[LOG] PID gain: Kp={request.pid.kp}, Ki={request.pid.ki}, Kd={request.pid.kd}")

    mock_results = [
        TimePoint(time=0.0, process_variable=0.0, setpoint=request.setpoint),
        TimePoint(time=0.1, process_variable=10.0, setpoint=request.setpoint),
        TimePoint(time=0.2, process_variable=25.0, setpoint=request.setpoint),
    ]

    return SimulationResponse(results=mock_results)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8001)