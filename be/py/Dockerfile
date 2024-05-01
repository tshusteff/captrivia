# Use the official Python base image
FROM python:3.12

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file to the working directory
COPY requirements.txt .

# Install the Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the application code to the working directory
COPY . .

# Expose the port on which the Flask application will run
EXPOSE 8080

# Set the entry point command to run the Flask application
CMD ["python", "run.py", "--host", "0.0.0.0"]
