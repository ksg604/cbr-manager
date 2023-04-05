FROM python:3.11
ENV PYTHONUNBUFFERED=1
WORKDIR /code
COPY requirements.txt /code/
RUN pip install -r requirements.txt
COPY . /code/
ENV PATH "$PATH:/code"
ENV PYTHONPATH "$PYTHONPATH:/code"
RUN chmod +x /code/scripts/*
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
EXPOSE 8000

