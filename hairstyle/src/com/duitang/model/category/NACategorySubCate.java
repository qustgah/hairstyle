package com.duitang.model.category;

import java.io.Serializable;

public class NACategorySubCate  implements Serializable
{ 
	private static final long serialVersionUID = 1L;

private String id;

  private String name;

  private String path;

  private String target;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPath()
  {
    return this.path;
  }

  public String getTarget()
  {
    return this.target;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPath(String paramString)
  {
    this.path = paramString;
  }

  public void setTarget(String paramString)
  {
    this.target = paramString;
  }
}

/* Location:           C:\Users\Administrator\Desktop\杂项\杂项-桌面残余-14-12-30\Android 逆助手\duitangmain_v3.3.2_dex2jar.jar
 * Qualified Name:     com.duitang.main.model.category.NACategorySubCate
 * JD-Core Version:    0.6.0
 */