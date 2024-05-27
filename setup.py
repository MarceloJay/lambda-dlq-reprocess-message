from setuptools import setup, find_packages

setup(
    name='dql_handler',
    version='0.1',
    packages=find_packages(),
    install_requires=[
        'boto3'
    ],
    description='Library to handle DLQ messages for AWS SQS',
    author='Your Name',
    author_email='your.email@example.com',
    url='https://github.com/yourusername/dql_handler',
    classifiers=[
        'Programming Language :: Python :: 3',
        'License :: OSI Approved :: MIT License',
        'Operating System :: OS Independent',
    ],
)