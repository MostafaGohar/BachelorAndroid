class CategoriesController < ApplicationController

  
  def index
    @categories=Category.all
  end

  
  def edit
    @category=Category.find(params[:id])
  end

  def destroy
     @category=Category.find(params[:id])
     @category.destroy
     redirect_to cateogories_path
  end

  def update
  @category = Category.find(params[:id])
 
    if @category.update_attributes(category_params)
      flash[:success] = "Your Category has been updated successfully !"
      redirect_to @category
    else
      render 'edit'
     
    end
 
end


def show
    @category = Category.find(params[:id])
    @user=User.find(2) ##should be taken from sessions
    if(@user.topic_id!=nil) 
    @topic=Topic.find(@user.topic_id)
    @can=0
    if(@topic.category_id== @category.id)
      @can=1
    end 
  end 
    @posts=Post.where(desttype:1,destid:@category.id)
  end



  def new
    @category = Category.new
  end



  def create
    
  end



  private
    def category_params
      params.require(:category).permit(:name)
    end
end
