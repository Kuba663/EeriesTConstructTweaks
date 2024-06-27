package pl.net.eerie.tictweaks.book;

import org.jetbrains.annotations.NotNull;
import pl.net.eerie.tictweaks.TiCTweaks;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class ModifiersSectionTransformer extends SectionTransformer {
    public ModifiersSectionTransformer() {
        super("modifiers");
    }

    @Override
    public void transform(BookData bookData, @NotNull SectionData sectionData) {
        ContentListing listing = (ContentListing) sectionData.pages.get(0).content;
        for (IModifier mod:
             Modifiers.modifiers) {
            PageData data = new PageData();
            data.source = new FileRepository((TiCTweaks.MOD_ID+":book").intern());
            data.parent = sectionData;
            data.type = "modifier";
            data.data = String.format("modifiers/%s.json", mod.getIdentifier());
            sectionData.pages.add(data);
            data.load();
            listing.addEntry(mod.getLocalizedName(), data);
        }
    }
}
