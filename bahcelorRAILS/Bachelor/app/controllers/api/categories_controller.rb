class Api:: CategoriesController <ApplicationController
respond_to :json
  skip_before_filter  :verify_authenticity_token
  def index
    @categories=Category.all
    respond_with  @categories
  end
  
  def show
    @category = Category.find(params[:id])
    
  end
  
  def posts
      @posts = Post.where(desttype: 2).where(destid: params[:id])
  end
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  end