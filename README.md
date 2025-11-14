# PID Tuner API

A backend system for modeling industrial processes and simulating PID (Proportional-Integral-Derivative) controllers. The project uses a microservices architecture to decouple data management (Java) from intensive numerical computation (Python).

## Architecture

* **Core API (Java/Spring Boot):** Manages plant models, data persistence, and orchestration.
* **Simulation Engine (Python/FastAPI):** Handles the ODE (Ordinary Differential Equation) solving and PID algorithm logic using `SciPy` and `NumPy`.
* **Database (PostgreSQL):** Stores plant configurations and simulation history.

## Prerequisites

* Java 21
* Maven
* Python 3.8+
* Docker & Docker Compose

## Setup & Run

### 1. Database (Docker)

Start the PostgreSQL container from the Java project root:

```
cd api
docker-compose up -d
```
2. Simulation Engine (Python)
Install dependencies and start the service on port 8001.
```
cd pid-simulation-service
```
## Create and activate virtual env
```
python3 -m venv venv
source venv/bin/activate  # Linux
```

## Install dependencies
```
pip install "fastapi[all]" uvicorn scipy numpy
```

## Run server
```
uvicorn main:app --reload --port 8001
```
3. Core API (Java)
Ensure the Python service is running before starting the API.

```
cd api
mvn spring-boot:run
```
The API will be available at http://localhost:8080.

#
## **Key Endpoints**
1. Create a Plant Model
```
POST /api/v1/plants
```

```
{
  "name": "Industrial Oven",
  "gain": 1.5,
  "timeConstant": 10.0,
  "deadTime": 2.0
}
```
2. Run Simulation
```
POST /api/v1/simulations
```

```
{
  "plantId": "YOUR-PLANT-UUID",
  "kp": 2.0,
  "ki": 0.5,
  "kd": 0.1,
  "setpoint": 100.0,
  "duration": 60.0
}
```