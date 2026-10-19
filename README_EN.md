# The Nyctocosm — Unofficial Native Remake (Forge 1.20.1)

Dear Flubberschnub,

This document introduces an unofficial, community-made **native Forge 1.20.1 remake** of your Nyctocosm datapack + resourcepack (v0.2.0). It is written to give you a clear picture of what this project is, how it was built, and how it relates to your original work.

**Repository**: https://github.com/19136644525lxy/The-Nyctocosm-Unofficial-remake
**Maintainer**: Yifei (with contributors EchoAria and community members)
**Original author**: [Flubberschnub](https://www.youtube.com/@FlubberschnubSchnyxteboutle) (YouTube)
**Contributor**: EchoAria

***

## 1. Attribution & License

This project has received **explicit permission** from Flubberschnub to maintain the repository, distribute the Forge mod, and continue expanding it.

- **Original datapack and all original assets** (textures, models, sounds, text, art): owned by **Flubberschnub**, who retains ownership of the original datapack.
- **This native Forge implementation and code**: owned by **Yifei, EchoAria, and the project contributors**.
- Your work is credited in every place a mod can carry attribution: `gradle.properties` (`mod_authors=Yifei, Flubberschnub, EchoAria`), `mods.toml`, and both READMEs.
- The mod re-distributes your textures, sounds, models, and text **inside its JAR** (so players do not need to install the original resourcepack separately).
- **License**: This project is licensed under the **Apache License 2.0** (see the [`LICENSE`](LICENSE) file). The original datapack and all original assets remain the property of Flubberschnub and are redistributed with explicit permission.
- **Distribution requirement**: credit and a link to the original Nyctocosm project must be kept in the README and on every distribution platform (CurseForge, Modrinth, etc.). The mod will remain freely available and open to community contributions.
- The modid `nyctocosm` is intentionally identical to your namespace, and the mod refuses to coexist with your datapack in a single world to prevent conflicts.

## 2. Why a Native Remake?

Your datapack (1.20.5+ commands) does not run on 1.20.1, and many players remain on 1.20.1 for mod compatibility. Rather than backporting command functions, this project **re-implements the entire experience in Java** on Forge, so that:

- Everything runs as native game code — no function-call overhead, no scoreboard bookkeeping visible to players.
- The 12 custom rigs with 5,607 sampled animation frames are rendered through a purpose-built animation system (below), giving bosses full skeletal animation without external animation libraries.
- Behavior can be regression-tested automatically.

## 3. Technical Approach

| Area | Approach |
| ---- | ---- |
| Target | Minecraft 1.20.1, Forge 47.4.27, Java 17 |
| Casting | The **Seelic Field** system — throwing a material item with the Soul Bottle opens a casting field; spells resolve when the cast finishes (faithful to your datapack's main path) |
| Artifacts | Pure Java inventory detection; no Curios/API dependency — "held = active", matching your predicate semantics |
| Animation | `native_rigs`: your Animated Java rigs are exported offline into per-frame sampled matrices (`*.json.gz`), then interpolated at runtime (TRS) — no GeckoLib |
| Dimensions | 3 custom dimensions (`nyctocosm`, `dusk_plane`, `emissary_domain`) with your worldgen presets, biomes, and structures ported as JSON |
| Regression | **292 automated GameTests** run in a dedicated game-test server before every change is accepted |

## 4. What Is Faithfully Restored

Essentially the complete playable flow of the datapack:

- **Entry**: the Ancient City portal, opened by a Resonance cast with an Echo Shard; return trip via an Eye of Ender cast (Evanescence).
- **Soul economy**: Scythe kills scatter souls; glass bottles collect them into Soul Bottles; the bottle is the casting focus (Planar Coil is the mobile variant).
- **All six elements + special spells**: dream / radiance / resonance / nightmare / void / dissonance bolts with per-element visuals and effects, Submergence (4 tendrils, slow + drag-through-terrain, shared finish damage), Reflectance (ownership transfer, power-scaled duration/capacity), Hypertrace (right-click pickup, sneak-collect-all, destroy interactions), Incurrence (Wither Rose boss summoning per biome).
- **Spell attributes**: a 7-domain stat system (global + six elements; power / speed / trace chance / resistance / weakness) mirroring `calcstatsspell`, including weakness/resistance damage modification and kill/damage/spell trackers.
- **All 13 artifacts** with their interactions, including the three Hypertrace-destroy combos (heal burst / soul reap / expulsion blast with power scaling).
- **All three bosses** with multi-part hitboxes, phases, entrance sequences, and the Emissary's six named attacks restored move-by-move against your mcfunction files (sword plunge, pillars, wing blast, flame slash, flame geysers, apocalypse rain — including fire-field fills and shockwave rings).
- **Ordinary mobs**: Nightmare Apparitions and Death Walkers spawning via phantom/illusioner replacement in Night Wastes / Cimmerian Gorge, with alertness rules, ambient particles, and clifftop walk checks.
- **Dais of Inculcation**: your 7×7 structure (beacon + anvil + quartz stairs + waxed copper slabs + 12 lit candles), the four recipes at your trader prices, and the ritual presentation (construct sound, hum loop, product-burst particles and radial ring).
- **Tony**: the black cat event inside the Dream Crypt (obfuscated name styling, collar, despawn drop), plus the LoreItem chapter system with your `nextentry` progression.
- **Structures & loot**: Slatehenge, Monolith, Dream Crypt, Outpost, Nightmare Fossil — including artifact loot pools.
- **Localization**: English + Simplified Chinese.

## 5. Intentional Deviations (Documented)

A small number of changes are deliberate:

1. **Casting entry**: your "candle ring → Spell Plane" ritual is merged into the direct "throw material with Soul Bottle" path (your candle ring belongs to the dais scene; players found two casting entries confusing).
2. **Dais activation**: activated by casting toward the anvil instead of right-clicking it (the vanilla anvil GUI intercepts right-clicks; unified with the casting path above).
3. **Tony search radius**: your ±300/±30 scan had a ~0.5% success rate per attempt; the remake searches ±3 around the player so the event actually fires in play.
4. **Boss relic drops**: the remake adds deterministic per-boss artifact drops (per your biome/boss theming) on top of your structure-chest loot, so the 13 artifacts are all reachable in survival.

Everything else — formulas, radii, timings, sound sources (`master @a[distance=..N]`), particle counts — was ported line-by-line against your mcfunction files.

## 6. Quality Assurance

- **292 GameTests** cover boss NBT persistence, spell lifecycles, artifact interactions, loot tables, structure loading, and original-function parity checks (the serpent-body tests literally diff against your datapack source).
- Every change must pass: build → clean game-test world → `All 292 required tests passed`.
- Visual-heavy changes are additionally verified in a live client.

We would be honored to hear any feedback or corrections from you. If you would like any change — attribution wording, asset handling, naming, or removal — please reach out; this project exists only because of your original creation.

— Yifei & contributors
