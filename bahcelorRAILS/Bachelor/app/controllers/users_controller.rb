class UsersController < ApplicationController
	def new 
		@user=User.new
	end
	def create
		@user=User.new(user_params)
		@user.save
	end

	def index
	#	@user = User.find(params[:id]) if User.exists?(params[:id])
	end
	
	
	def edit

	end 

	def delete
	end
	
	def followers
		@ids=Follow.where(followee_id:params[:id])
		@followers=User.where(id:@ids)
	end 
	
	def followees 
		@ids=Follow.where(follower_id:params[:id])
		@followees=User.where(id:@ids)
	end 

	def show
		@user=User.find(params[:id]) #the person whose timeline is open 
		@posts=Post.where(:destid=>1,:desttype=>0)
		@user2=User.find(2)  #should be got from the session 
		@can=0
		@isfollow=Follow.where(follower_id:@user2.id , followee_id:@user.id)
		@isfollow2=Follow.where(follower_id:@user.id ,followee_id:@user2.id) 
		if(@isfollow.present?||@isfollow2.present?)
			@can=1
		end 
		
	end 
	protected def user_params
		user_params = User.new(params.require(:user).permit(:email, :gucid, :fname , :lname , :dob ,:gender, :location, :avatar))
		
	end
end
