class Api:: PostsController <ApplicationController
respond_to :json
  skip_before_filter  :verify_authenticity_token
  def index
    @posts=Post.all
    respond_with  @posts
  end

  
  def edit
    @post=Post.find(params[:id])
    respond_with @post
  end

  def destroy
     @post=Post.find(params[:id])
     @post.destroy
     redirect_to posts_path
  end

  def update
  @post = Post.find(params[:id])
 
    if @post.update_attributes(post_params)
      flash[:success] = "Your post has been updated successfully !"
      redirect_to @post
    else
      render 'edit'
     
    end
 
  end


  def show
    @post = Post.find(params[:id])
    @user=@post.user
    @comments=@post.comments
  end



  def new
    @post = Post.new
  end



  def create
    @post = Post.new
    @post.title = params[:title]
    @post.content=params[:post][:content]
    @post.desttype=params[:desttype]
    @post.destid=params[:destid]
    @post.user_id=params[:user][:id] ##should be taken from the session
    if @post.save
      flash[:success] = "Your post has been created successfully !"
      #redirect_to :back
    else
      #render 'new'
    end
  end



  private
    def post_params
      params.require(:post).permit(:title, :content, :desttype,
                                   :dest_id,:user_id)
    end
end
