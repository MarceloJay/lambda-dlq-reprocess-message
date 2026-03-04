# DLQ Handler Library

A library for handling DLQ (Dead Letter Queue) messages in AWS SQS. This library allows you to reprocess messages from a DLQ with specified parameters such as the queue URL, original queue URL, and maximum number of attempts.

## Installation

To install the library, use `pip`:

```sh
pip install dlqhandler

from dlqhandler import ProcessMessage

# Initialize the DLQHandler with the required parameters
process_message_handler = ProcessMessage(
    dlq_queue_url='https://sqs.us-east-1.amazonaws.com/123456789012/my-dlq',
    original_queue_url='https://sqs.us-east-1.amazonaws.com/123456789012/my-queue',
    max_attempts=5,
    region_name='us-east-1',
    env=my_env_config,  # replace with your actual environment config
    nome_lambda='lambda-reprocessamento-dlq',
    namespace='DLQ-Mensageria'
)

# Process the messages from the DLQ
process_message_handler.execute()


https://pypi.org/account/confirm-login/?token= . eJw1jdsOwiAQRH_ F8Krb7AKFws80dAFDUmns5cn472KNj zOTc- YlAu9lqcKLebmXCrzUXNZHOMubOLa0 diW22aAkI5EgRougFToIPTKQZeKsKE _R_oE5bPt4- hooURpACeguiJ6cl6ZzTYR4bRnxC9X yPNKPGM- 3wFYZaydINmnQPWsYBk2QKFGvlaGQp Xh_APVfNag.aahtqw. evG6Oy4zyhAy_ KB4rj2auCaoHeLmenng9fnA0BzofAC cikNQlY8zz-XeorZYL61hz2_ HjFMd6mFwxeh9zPxXXA
