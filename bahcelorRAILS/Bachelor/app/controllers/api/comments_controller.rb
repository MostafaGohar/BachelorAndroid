class Api:: CommentsController <ApplicationController
respond_to :json
  skip_before_filter  :verify_authenticity_token
  
  def create
  @comment = Comment.new
  @comment.content = params[:content]
  @comment.user_id = params[:user][:id]
  @comment.post_id = params[:post][:id]
  @comment.save
  end
  
  
  
  
  end