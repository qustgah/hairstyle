package com.duitang.model.category;

import java.util.List;

public class NAEntranceGroupItem
{

  private List<NAEntranceGroupSubItem> items;

  private String name;

  public List<NAEntranceGroupSubItem> getItems()
  {
    return this.items;
  }

  public String getName()
  {
    return this.name;
  }

  public void setItems(List<NAEntranceGroupSubItem> paramList)
  {
    this.items = paramList;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }
}