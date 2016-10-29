.PHONY: clean

run:
	python3 manage.py makemigrations FeederApp
	python3 manage.py migrate
	python3 manage.py runserver