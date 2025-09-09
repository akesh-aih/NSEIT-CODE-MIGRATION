package com.nseit.generic.models;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MenuBean
{
	private String menuKey;
	private String displayName;
	private String menuLink;
	private MenuBean parentMenu;
	private SortedMap<String, MenuBean> subMenuMap;
	
	public MenuBean()
	{
		
	}
	public MenuBean(String menuKey, String displayName, String menuLink)
	{
		this.menuKey = menuKey;
		this.displayName = displayName;
		this.menuLink = menuLink;
		subMenuMap = new TreeMap<String, MenuBean>();
	}
	
	public boolean isParentExists(String key)
	{
		boolean parentExists = false;
		if(menuKey.compareTo(key)==0)
			parentExists = true;
		else if(subMenuMap.containsKey(key))
			parentExists = true;
		else
		{
			for(Entry<String, MenuBean> subMenuEntry : subMenuMap.entrySet())
			{
				if(subMenuEntry.getValue().isParentExists(key))
				{
					parentExists = true;
					break;
				}
			}
		}
		
		return parentExists;
	}
	
	public void addSubMenu(String parentKey ,MenuBean subMenu)
	{
		if(this.menuKey.compareTo(parentKey)==0)
		{
			subMenuMap.put(subMenu.getMenuKey(), subMenu);
			subMenu.setParentMenu(this);
		}
		else if(subMenuMap.containsKey(parentKey))
		{
			subMenuMap.get(parentKey).addSubMenu(parentKey, subMenu);
		}
	}

	public String getMenuKey()
	{
		return menuKey;
	}

	public void setMenuKey(String menuKey)
	{
		this.menuKey = menuKey;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getMenuLink()
	{
		return menuLink;
	}

	public void setMenuLink(String menuLink)
	{
		this.menuLink = menuLink;
	}

	public MenuBean getParentMenu()
	{
		return parentMenu;
	}

	public void setParentMenu(MenuBean parentMenu)
	{
		this.parentMenu = parentMenu;
	}

	public SortedMap<String, MenuBean> getSubMenu()
	{
		return subMenuMap;
	}

	public void setSubMenu(SortedMap<String, MenuBean> subMenu)
	{
		this.subMenuMap = subMenu;
	}
}
