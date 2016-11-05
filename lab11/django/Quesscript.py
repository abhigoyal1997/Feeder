from django.db import models
from FeederApp.models import Question
question = Question.objects.create(question="Do you have any comments or suggestions you would like to share about the instructor or the course?",question_type="TF")
question.save()
question = Question.objects.create(question="Overall, the course was well organized.",question_type="RB")
question.save()
question = Question.objects.create(question="The instructor was well prepared for each class.",question_type="RB")
question.save()
question = Question.objects.create(question="Feedback on curriculum/course content",question_type="TF")
question.save()