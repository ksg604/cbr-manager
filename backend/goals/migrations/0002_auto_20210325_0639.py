# Generated by Django 3.1.5 on 2021-03-25 06:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('goals', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='goal',
            name='datetime_completed',
            field=models.DateTimeField(blank=True, null=True),
        ),
    ]