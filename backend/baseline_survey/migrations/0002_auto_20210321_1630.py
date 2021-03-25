# Generated by Django 3.1.5 on 2021-03-21 23:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('baseline_survey', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='baselinesurvey',
            name='health_satisfaction',
            field=models.CharField(blank=True, choices=[('Very Unsatisfied', 'Very Unsatisfied'), ('Unsatisfied', 'Unsatisfied'), ('Satisfied', 'Satisfied'), ('Very Satisfied', 'Very Satisfied')], max_length=50),
        ),
    ]