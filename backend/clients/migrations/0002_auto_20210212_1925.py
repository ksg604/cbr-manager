# Generated by Django 3.1.5 on 2021-02-13 03:25

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('clients', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='client',
            name='education_risk',
            field=models.IntegerField(max_length=30),
        ),
        migrations.AlterField(
            model_name='client',
            name='health_risk',
            field=models.IntegerField(max_length=30),
        ),
        migrations.AlterField(
            model_name='client',
            name='social_risk',
            field=models.IntegerField(max_length=30),
        ),
    ]