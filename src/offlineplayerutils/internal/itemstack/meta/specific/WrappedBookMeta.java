package offlineplayerutils.internal.itemstack.meta.specific;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.meta.BookMeta;

import offlineplayerutils.internal.itemstack.meta.WrappedItemMeta;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagType;

public class WrappedBookMeta extends WrappedItemMeta implements BookMeta {

	private static final String AUTHOR_TAG = "author";
	private static final String TILTE_TAG = "title";
	private static final String PAGES_TAG = "pages";

	public WrappedBookMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	@Override
	public WrappedBookMeta clone() {
		return new WrappedBookMeta((NBTTagCompound) itemmetatag.clone());
	}

	@Override
	public boolean hasAuthor() {
		return itemmetatag.hasOfType(AUTHOR_TAG, NBTTagType.STRING);
	}

	@Override
	public String getAuthor() {
		if (hasAuthor()) {
			return itemmetatag.getString(AUTHOR_TAG);
		}
		return null;
	}

	@Override
	public void setAuthor(String author) {
		if (author == null || author.isEmpty()) {
			itemmetatag.remove(AUTHOR_TAG);
		} else {
			itemmetatag.setString(AUTHOR_TAG, author);
		}
	}

	@Override
	public boolean hasTitle() {
		return itemmetatag.hasOfType(TILTE_TAG, NBTTagType.STRING);
	}

	@Override
	public String getTitle() {
		if (hasTitle()) {
			return itemmetatag.getString(TILTE_TAG);
		}
		return null;
	}

	@Override
	public boolean setTitle(String title) {
		if (title == null || title.isEmpty()) {
			itemmetatag.remove(AUTHOR_TAG);
		} else {
			itemmetatag.setString(AUTHOR_TAG, title);
		}
		return true;
	}

	@Override
	public boolean hasPages() {
		return itemmetatag.hasListOfType(PAGES_TAG, NBTTagType.STRING);
	}

	@Override
	public int getPageCount() {
		return getPages().size();
	}

	@Override
	public List<String> getPages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPage(String... addpages) {
		List<String> pages = getPages();
		for (String addpage : pages) {
			pages.add(addpage);
		}
	}

	@Override
	public String getPage(int index) {
		return getPages().get(index);
	}

	@Override
	public void setPage(int index, String page) {
		List<String> pages = getPages();
		pages.set(index, page);
		savePages(pages);
	}

	@Override
	public void setPages(String... pages) {
		setPages(Arrays.asList(pages));
	}

	@Override
	public void setPages(List<String> pages) {
		savePages(pages);
	}

	private void savePages(List<String> pages) {
	}	

}
