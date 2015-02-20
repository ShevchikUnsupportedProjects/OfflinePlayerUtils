/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package offlineplayerutils.internal.itemstack.meta;

import java.util.ArrayList;
import java.util.List;

import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BannerMeta;

public class WrappedBannerMeta extends WrappedItemMeta implements BannerMeta {

	private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
	private static final String BASE_TAG = "Base";
	private static final String PATTERNS_TAG = "Patterns";
	private static final String PATTERN_PATTERN_TAG = "Pattern";
	private static final String PATTERN_COLOR_TAG = "Color";

	public WrappedBannerMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	private NBTTagCompound getBlockEntityTag() {
		if (itemmetatag.hasOfType(BLOCK_ENTITY_TAG, NBTTagType.COMPOUND)) {
			return (NBTTagCompound) itemmetatag.get(BLOCK_ENTITY_TAG);
		}
		return new NBTTagCompound();
	}

	private void saveBlockEntityTag(NBTTagCompound tag) {
		if (tag.size() == 0) {
			itemmetatag.remove(BLOCK_ENTITY_TAG);
		} else {
			itemmetatag.set(BLOCK_ENTITY_TAG, tag);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public DyeColor getBaseColor() {
		NBTTagCompound blockentitytag = getBlockEntityTag();
		if (blockentitytag.hasOfNumberType(BASE_TAG)) {
			return DyeColor.getByDyeData((byte) blockentitytag.getInt(BASE_TAG));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBaseColor(DyeColor color) {
		NBTTagCompound blockentitytag = getBlockEntityTag();
		if (color != null) {
			blockentitytag.setInt(BASE_TAG, color.getDyeData());
		} else {
			blockentitytag.remove(BASE_TAG);
		}
		saveBlockEntityTag(blockentitytag);
	}

	@Override
	public int numberOfPatterns() {
		return getPatterns().size();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Pattern> getPatterns() {
		NBTTagCompound blockentitytag = getBlockEntityTag();
		if (blockentitytag.hasListOfType(PATTERNS_TAG, NBTTagType.COMPOUND)) {
			ArrayList<Pattern> patterns = new ArrayList<Pattern>();
			for (NBTTagCompound pattern : (NBTTagList<NBTTagCompound>) blockentitytag.get(PATTERNS_TAG)) {
				patterns.add(new Pattern(DyeColor.getByDyeData((byte) pattern.getInt(PATTERN_COLOR_TAG)), PatternType.getByIdentifier(pattern.getString(PATTERN_PATTERN_TAG))));
			}
			return patterns;
		}
		return new ArrayList<Pattern>();
	}

	@Override
	public Pattern getPattern(int index) {
		return getPatterns().get(index);
	}

	@Override
	public void addPattern(Pattern pattern) {
		List<Pattern> patterns = getPatterns();
		patterns.add(pattern);
		savePatterns(patterns);
	}

	@Override
	public void setPattern(int index, Pattern pattern) {
		List<Pattern> patterns = getPatterns();
		patterns.set(index, pattern);
		savePatterns(patterns);
	}

	@Override
	public Pattern removePattern(int index) {
		List<Pattern> patterns = getPatterns();
		Pattern removed = patterns.remove(index);
		savePatterns(patterns);
		return removed;
	}

	@Override
	public void setPatterns(List<Pattern> patterns) {
		savePatterns(patterns);
	}

	@SuppressWarnings("deprecation")
	private void savePatterns(List<Pattern> patterns) {
		NBTTagCompound blockentitytag = getBlockEntityTag();
		if (patterns.size() == 0) {
			blockentitytag.remove(PATTERNS_TAG);
		} else {
			NBTTagList<NBTTagCompound> patternstag = new NBTTagList<NBTTagCompound>();
			for (Pattern pattern : patterns) {
				NBTTagCompound patterntag = new NBTTagCompound();
				patterntag.setString(PATTERN_PATTERN_TAG, pattern.getPattern().getIdentifier());
				patterntag.setInt(PATTERN_COLOR_TAG, pattern.getColor().getDyeData());
			}
			blockentitytag.set(PATTERNS_TAG, patternstag);
		}
		saveBlockEntityTag(blockentitytag);
	}

}
