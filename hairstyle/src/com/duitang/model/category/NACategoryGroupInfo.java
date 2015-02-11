package com.duitang.model.category;

import java.util.ArrayList;

public class NACategoryGroupInfo
{

  private String contentType;

  private String groupId;

  private ArrayList<NACategoryItemInfo> groupItems;

  private String groupName;

  public String getContentType()
  {
    return this.contentType;
  }

  public String getGroupId()
  {
    return this.groupId;
  }

  public ArrayList<NACategoryItemInfo> getGroupItems()
  {
    return this.groupItems;
  }

  public String getGroupName()
  {
    return this.groupName;
  }

  public void setContentType(String paramString)
  {
    this.contentType = paramString;
  }

  public void setGroupId(String paramString)
  {
    this.groupId = paramString;
  }

  public void setGroupItems(ArrayList<NACategoryItemInfo> paramArrayList)
  {
    this.groupItems = paramArrayList;
  }

  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }
}
