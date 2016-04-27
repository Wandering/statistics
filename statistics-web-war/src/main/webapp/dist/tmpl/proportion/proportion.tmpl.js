define(function(require, exports, module) {

  <div id="add_role" class="form-horizontal">
      <div class="control-group">
      <label class="control-label" for="role_name">角色名称:</label>
  <div class="controls">
      <input class="form-control form-w200" type="text" id="role_name" placeholder="请输入角色名称" />
      </div>
      <p class="form-error"></p>
      </div>
      <div class="control-group">
      <label class="control-label" for="role_description">角色描述:</label>
  <div class="controls">
      <textarea class="form-control form-w200" rows="3" id="role_description" placeholder="请输入角色描述"></textarea>
      </div>
      <p class="form-error"></p>
      </div>
      <div class="control-group">
      <label class="control-label">选择资源:</label>
  <div class="controls form-w200">
      <ul id="tree_resource" class="ztree" style="background-color:rgba(204,102,0,.4)"></ul>
      </div>
      <p class="form-error"></p>
      </div>
      <div id="model_error_msg"></div>
      </div>

});