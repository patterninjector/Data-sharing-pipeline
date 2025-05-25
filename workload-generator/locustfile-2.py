from locust import HttpUser, task, between
import random

class PipelineLoadTest(HttpUser):
    host = "http://localhost:8080"
    wait_time = between(1, 3)

    # Test modes and paths
    test_modes = {
        "normal": {
            "path": "pipelines/normal/",
            "expected_status": [200],
            "weight": 3  # 30% of traffic
        },
        "flaky": {
            "path": "pipelines/flaky/",
            "expected_status": [200, 500],
            "weight": 2  # 20% of traffic
        },
        "slow": {
            "path": "pipelines/slow/",
            "expected_status": [200],
            "weight": 3  # 30% of traffic
        },
        "broken": {
            "path": "pipelines/block/",
            "expected_status": [404, 502, 504],
            "weight": 2  # 20% of traffic
        }
    }

    @task
    def execute_pipeline(self):
        # Weighted random selection
        mode = random.choices(
            list(self.test_modes.keys()),
            weights=[v['weight'] for v in self.test_modes.values()],
            k=1
        )[0]

        config = self.test_modes[mode]
        pipeline_file = f"{config['path']}book_url_pipeline_{random.randint(1, 4)}.json"

        with self.client.post(
                "/run-pipeline-path-url",
                json={
                    "path": pipeline_file,
                },
                catch_response=True
        ) as response:
            if response.status_code not in config['expected_status']:
                response.failure(f"Unexpected status {response.status_code} for mode {mode}")
            else:
                response.success()
