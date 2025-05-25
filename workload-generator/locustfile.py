from locust import HttpUser, task, between
import random
import time

class SpringLoadTest(HttpUser):
    wait_time = between(1, 3)

    # Sample payloads
    payloads = [
   #     {"path": "classpath:pipelines/"},
   #     {"path": "classpath:pipelines/"},
   #     {"path": "classpath:pipelines/"},
   #     {"path": "classpath:pipelines/"},
        {"path": "classpath:pipelines/classpath:pipelines/normal/pipeline.json"}
    ]

    @task
    def call_api(self):
        payload = random.choice(self.payloads)
        self.client.post("http://localhost:8080/run-pipeline-path", json=payload)

