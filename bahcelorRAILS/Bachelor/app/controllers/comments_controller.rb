class CommentsController < ApplicationController

def index
        @comments=Comment.where(:post_id=>params(:id))
  end


  def destroy
     @comment=Comment.find(params[:id])
     @comment.destroy
     render :back
  end


  def new
    @commnet = Comment.new
  end

def create

    @comment = Comment.new(comment_params)
    @comment.post_id=params[:post_id]

    if @comment.save
      #redirect_to :back
      redirect_to :back
     #render Rails.application.routes.recognize_path(request.referer)[:action]
    else
      redirect_to :back
    end
  end



  private
    def comment_params
      params.require(:comment).permit(:content,:post_id)#have to add user_id after the session
    end
end

