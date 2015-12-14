
collection @posts , object_root: false
attributes :id ,:title, :content , :created_at,:desttype,:destid
child :user do 
   attributes :id, :fname ,:lname,:doctor
end


