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