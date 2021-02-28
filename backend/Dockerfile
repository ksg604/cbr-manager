FROM python:3
ENV PYTHONUNBUFFERED=1
WORKDIR /code
COPY requirements.txt /code/
RUN pip install -r requirements.txt
COPY . /code/
ENV PATH "$PATH:/code"
ENV PYTHONPATH "$PYTHONPATH:/code"
RUN chmod +x /code/scripts/*

