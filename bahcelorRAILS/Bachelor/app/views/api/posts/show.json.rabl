object @post
attributes :id, :content, :title
child :user do 
  attributes :id, :fname ,:lname,:doctor
end
child :comments do 
collection @comments, object_root: false
attributes :id , :content , :created_at
child :user do 
  attributes :id, :fname ,:lname,:doctor
end
end 
