object @follow => nil 
attributes :id
child :user do 
    object @follower
   attributes :id, :fname ,:lname,:doctor
end
child :user do 
    object @followee
   attributes :id, :fname ,:lname,:doctor
end