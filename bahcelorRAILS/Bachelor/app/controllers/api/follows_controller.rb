class Api:: FollowsController <ApplicationController
respond_to :json
  skip_before_filter  :verify_authenticity_token
 

  def unfollow
    @follow = Follow.where(follower_id: params[:follower][:id],followee_id: params[:followee][:id]).first
    #@follow = Follow.find(id: params[:id])
    @follow.destroy
  end
  
  


  def new
    @follow = Follow.new
  end



  def create
    @follow = Follow.new
    @follow.follower_id = params[:follower][:id]
    @follow.followee_id = params[:followee][:id]
    
   @follow.save
  end

    def exists
        @follow = Follow.where(follower_id: params[:follower][:id], followee_id: params[:followee][:id]).first
    end


  private
    def follow_params
      params.require(:follow).permit(:follower_id, :followee_id)
    end
end
