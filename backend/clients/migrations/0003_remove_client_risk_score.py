# Generated by Django 3.1.5 on 2021-04-03 08:38

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('clients', '0002_auto_20210314_1835'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='client',
            name='risk_score',
        ),
    ]