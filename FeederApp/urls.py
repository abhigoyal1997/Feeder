from django.conf.urls import url

from . import views

urlpatterns = [
	url(r'^$',views.login, name='login'),
	url(r'^register/',views.register,name='register'),
	url(r'^signup/',views.signup, name='signup'),
	url(r'^authstudent',views.auth_stud,name='auth_stud'),
	url(r'^authinstruct',views.auth_inst,name='auth_inst'),
	
	
	
]