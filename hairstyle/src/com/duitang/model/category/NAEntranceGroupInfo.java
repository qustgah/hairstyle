package com.duitang.model.category;

import java.util.List;

public class NAEntranceGroupInfo
{

  private List<NAEntranceGroupItem> groupItems;

  private String groupName;

  public List<NAEntranceGroupItem> getGroupItems()
  {
    return this.groupItems;
  }

  public String getGroupName()
  {
    return this.groupName;
  }

  public void setGroupItems(List<NAEntranceGroupItem> paramList)
  {
    this.groupItems = paramList;
  }

  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }
} 