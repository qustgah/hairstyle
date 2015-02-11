package com.duitang.model.category;

import java.util.ArrayList;

public class NACategoryDetailInfo
{

  private ArrayList<NACategorySubCate> NACategorySubCatesList;

  private String desc;

  private String id;

  private String is_show_price;

  private MallCategory mall_category;

  private String name;

  private String path;

  private String pic;

  private String short_name;

  private String tags;

  private String target;

  public String getDesc()
  {
    return this.desc;
  }

  public String getId()
  {
    return this.id;
  }

  public String getIs_show_price()
  {
    return this.is_show_price;
  }

  public MallCategory getMall_category()
  {
    return this.mall_category;
  }

  public ArrayList<NACategorySubCate> getNACategorySubCatesList()
  {
    return this.NACategorySubCatesList;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPath()
  {
    return this.path;
  }

  public String getPic()
  {
    return this.pic;
  }

  public String getShort_name()
  {
    return this.short_name;
  }

  public String getTags()
  {
    return this.tags;
  }

  public String getTarget()
  {
    return this.target;
  }

  public void setDesc(String paramString)
  {
    this.desc = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setIs_show_price(String paramString)
  {
    this.is_show_price = paramString;
  }

  public void setMall_category(MallCategory paramMallCategory)
  {
    this.mall_category = paramMallCategory;
  }

  public void setNACategorySubCatesList(ArrayList<NACategorySubCate> paramArrayList)
  {
    this.NACategorySubCatesList = paramArrayList;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPath(String paramString)
  {
    this.path = paramString;
  }

  public void setPic(String paramString)
  {
    this.pic = paramString;
  }

  public void setShort_name(String paramString)
  {
    this.short_name = paramString;
  }

  public void setTags(String paramString)
  {
    this.tags = paramString;
  }

  public void setTarget(String paramString)
  {
    this.target = paramString;
  }

  public class MallCategory
  {

    private String id;

    private String is_show_price;

    private String name;

    public MallCategory()
    {
    }

    public String getId()
    {
      return this.id;
    }

    public String getIs_show_price()
    {
      return this.is_show_price;
    }

    public String getName()
    {
      return this.name;
    }

    public void setId(String paramString)
    {
      this.id = paramString;
    }

    public void setIs_show_price(String paramString)
    {
      this.is_show_price = paramString;
    }

    public void setName(String paramString)
    {
      this.name = paramString;
    }
  }
}
