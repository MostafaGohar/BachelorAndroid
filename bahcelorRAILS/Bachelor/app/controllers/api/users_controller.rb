class Api:: UsersController <ApplicationController
    respond_to :json
    skip_before_filter  :verify_authenticity_token
    def show
		@user=User.find(params[:id]) #the person whose timeline is open 
# 		@posts=Post.where(:destid=>1,:desttype=>0)
# 		@user2=User.find(2)  #should be got from the session 
# 		@can=0
# 		@isfollow=Follow.where(follower_id:@user2.id , followee_id:@user.id)
# 		@isfollow2=Follow.where(follower_id:@user.id ,followee_id:@user2.id) 
# 		if(@isfollow.present?||@isfollow2.present?)
# 			@can=1
# 		end 
		
    end 
    def create
		@user=User.new
		@user.uid = params[:uid]
		@user.fname = params[:fname]
		@user.lname = params[:lname]
		@user.email = params[:email]
		@user.doctor = params[:doctor]
		@user.gender = params[:gender]
		@user.dob = params[:dob]
		@user.gucid = params[:gucid]
		@user.location = params[:location]
		@user.avatar = params[:avatar]
		@user.save
	end
	
	
    
    def exists
        @user = User.where(uid: params[:uid]).first
    end
    
    def timeline
	  @follows = Follow.where(:follower_id => params[:id])  #elly howa ana elcurrentuser id y3ny
	  array = Array.new
	  @follows.each do |f|
	  	array << f.followee_id
	  end
	  @posts1= Post.all.where(:desttype => 1,:destid=>params[:id])
	  @posts2=Post.where(user_id: array)
	  @posts=@posts1.concat(@posts2)
	  @posts.uniq!
  end
  
  def followers
		@followers=Follow.where(followee_id: params[:id])
		array = Array.new
	  @followers.each do |f|
	  	array << f.follower_id
	  end
		@followers=User.where(id: array)
	end 
	
	def followees 
		@followees=Follow.where(follower_id: params[:id])
		array = Array.new
	  @followees.each do |f|
	  	array << f.followee_id
	  end
		@followees=User.where(id: array)
	end 
end
