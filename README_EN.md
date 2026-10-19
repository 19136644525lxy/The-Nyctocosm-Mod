# Nyctocosm — Native Forge 1.20.1 Remake

A native Forge 1.20.1 remake of Flubberschnub's original *Nyctocosm* datapack + resourcepack (v0.2.0, MC 1.20.5+).

**Original author**: [Flubberschnub](https://www.youtube.com/@FlubberschnubSchnyxteboutle) (YouTube)
**Contributor**: EchoAria

## Licensing & Ownership (with explicit permission from the original author)

- **Original datapack and all original assets** (models / textures / sounds / text / art): copyright belongs to **Flubberschnub**, who retains ownership of the original datapack.
- **This native Forge implementation and code**: owned by **Yifei, EchoAria, and the project contributors**.
- Explicit permission has been granted: to maintain this repository, distribute the Forge mod, and continue expanding it.
- This project is licensed under the **Apache License 2.0** (see the [`LICENSE`](LICENSE) file). The original datapack and original assets remain the property of Flubberschnub and are redistributed with permission.
- Distribution requirement: when distributing this mod on any platform (GitHub / CurseForge / Modrinth, etc.), credit to the original author and a link to the original project must be retained; the mod remains free and open to community contributions.

- **Minecraft**: 1.20.1　**Loader**: Forge 47.4.27　**Java**: 17
- **modid**: `nyctocosm` (same namespace as the original datapack/resourcepack — **cannot be loaded together**)
- **Current version**: see `mod_version` in [gradle.properties](gradle.properties)

***

## 1. Building

```powershell
.\gradlew.bat assemble        # outputs JAR + *-sources.jar to build/libs/
```

- Do not prepend `clean` to the command unless a clean build is explicitly requested.
- JEI integration is a `compileOnly` dependency (BlameJared repository); JEI is optional at runtime. With JEI installed, altar recipes can be looked up in the inventory.

## 2. Running & Regression Tests (Development Environment)

```powershell
.\gradlew.bat runClient                # launch the development client
.\gradlew.bat runClient -Psmoke        # automated client smoke scenario
.\gradlew.bat runGameTestServer        # 292 GameTest regressions
```

**Regression environment protocol (must be followed, otherwise false failures occur):**
1. Before running `runGameTestServer`, you must delete `run\world\` — scoreboard objectives and test blocks persist to the world; reusing an old world cascades into multiple false test failures (boss_health / ice_spikes / source_passthrough / crypt_trigger).
2. Acceptance is determined by `All 292 required tests passed` in `run\logs\latest.log`, not just by BUILD SUCCESSFUL.
3. The `serpent_body` test series reads the external reference `f:\Work\_nyctocosm_source\datapack\` (extracted from `Nyctocosm Datapack v0.2.0.zip` in the project root).

***

## 3. Complete In-Game Flow

### 3.1 Portal → Main Dimension

1. **Prerequisite — obtain a Soul Bottle**: craft a **Seelic Scythe**, kill mobs with it to scatter souls on the ground, then right-click a soul while holding a **glass bottle** to collect it into a **Soul Bottle**.
2. **Locate the Ancient City portal**: the portal generates naturally inside the **Ancient City** structure in the Deep Dark of the Overworld, its frame covered in sculk veins — no need to build it yourself.
3. **Activate with Resonance**: throw an **Echo Shard** while holding the Soul Bottle to cast Resonance; a beam strikes the portal (three-wave opening sequence + sculk soul particles), filling the frame with `nyctocosm:portal` blocks.
4. Walk into the portal to enter the **nyctocosm main dimension**. For development, `/nyctocosm travel` can also be used for quick round trips (see command table).
5. **Return trip**: throw an **Eye of Ender** inside the dimension to cast Evanescence and teleport back to the Overworld — this is the only way to leave besides dying.

### 3.2 Spell System (Seelic Field Casting)

All player spells share a single casting chain — the **Seelic Field**:

1. Hold the **Soul Bottle**, right-click to throw the corresponding **casting material** (one material per spell).
2. A Seelic Field entity spawns on the ground and begins casting; rune particles appear during casting; throwing more materials stacks the cast.
3. When casting completes, the spell is released automatically (projectile / area effect).

**Six elements**: dream / radiance / resonance / nightmare / void / dissonance, each with its own projectile visuals and effects (burning / knockback / slowness / blindness, etc.).

**Special spells**:
- **Submergence**: summons 4 tendrils that slow and drag victims tick-by-tick (can pass through walls and into the ground); damage is resolved once when the effect ends.
- **Reflectance**: places a reflector that reverses the ownership of enemy projectiles; duration and capacity scale with power.
- **Hypertrace**: places a tracking mark (see 3.4 artifact interactions).
- **Incurrence**: throw a **Wither Rose** and cast for 9 seconds to summon the Boss in the corresponding biome (see 3.7).

**Spell attributes (SpellStats)**: 7 domains (global + six elements) × power / speed / tracechance / resistance / weakness, stored in player data. power increases projectile damage, speed shortens cast time, weakness/resistance modify incoming damage. Sources include artifacts and boss-drop progression.

### 3.3 Two Forms of the Soul Bottle

- **Soul Bottle**: the casting medium that opens a fixed Seelic Field; throw materials to cast.
- **Planar Coil**: opens a Seelic Field that follows the player (mobile casting form).

### 3.4 Artifacts (13 pieces)

**Acquisition**:
| Source | Drop |
| ---- | ---- |
| Slatehenge structure chest | 1 random of the 13 artifacts |
| Monolith structure chest | 30% chance of 1 random of the 13 artifacts |
| Serpent | protection_dream + absorption, 1 each |
| Sorcerer | protection_nightmare + levitation, 1 each |
| Emissary | protection_dissonance + expulsion, 1 each |
| Death Walker | 20% chance accumulation |
| Nightmare Apparition | 20% chance amelioration |

**Effects** (active while held in inventory):
- **absorption**: 20% life steal on melee hits.
- **accumulation**: +10% trace count per kill.
- **amelioration**: heals when casting in a Seelic Field.
- **levitation**: sneak in mid-air to slow-fall (costs 3 charges).
- **expulsion**: triggers an explosion area on enemy kills.
- **protection_* (6 types)**: incoming damage reduction for the corresponding element; consumes hypertrace charges (200-tick shared cooldown).

**Hypertrace interactions** (placed tracking mark entities):
- **Right-click**: reclaim +1 charge; **Sneak right-click**: collect all of your own hypertraces across all dimensions at once.
- **Left-click to destroy**: triggers the combo of held artifacts — with amelioration → heal 3.5 hearts + firework particles; with extraction → summon a soul above the head; with expulsion → power-scaled explosion (dissonance.power×3+7 damage); **Sneak left-click**: detonate all at once.

### 3.5 Weapons (4 pieces)

| Weapon | Acquisition | Ability |
| ---- | ---- | ---- |
| **Seelic Scythe** | Crafting: Echo Shard ×3 + Stick ×2 | Sneak + right-click dash (speed I); kills collect souls (soul spawns ahead of you + soul_reap sound) |
| **Wyrm's Liver** | Dais crafting | Right-click fires a projectile; hit applies blindness for 5 seconds |
| **Nullfissure** | Dais crafting | Main-hand swings a sonic wave; off-hand casts a spell bolt |
| **Semblance of Doom** | No survival acquisition in the original pack (command item) | Area sweep |

### 3.6 Dais — Ritual Crafting

**Structure layout** (7×7, centered on the beacon):
1. Center: **beacon**, with an **anvil** placed directly above it.
2. 1 block in four directions from the beacon: **block of quartz** ×4; 2 blocks out: **quartz stairs** ×4 (outward, bottom slab).
3. Four diagonals (±1,±1): **waxed cut copper slab** ×4.
4. Outer cross: **lit candles** ×12 (must be lit with flint and steel).

**Activation**: hold the Soul Bottle and **cast toward the anvil** (do not right-click the anvil — that opens the vanilla anvil GUI; the original datapack activated by right-clicking directly above the anvil, but this mod unifies the casting path so activation is done by casting). When the Seelic Field opens, it checks the full structure 1 block ahead; if valid, the anvil is replaced with the Dais block + construct presentation (30-block sound).

**Usage**: right-click the Dais to open the crafting GUI with 4 recipes (costs match the original pack's wandering trader prices, e.g. wyrmsliver / nullfissure). Taking the output triggers the original pack's presentation (flash + lava + explosion + 360° radial shockwave ring). When idle, the Dais emits a hum ambiance every 6 seconds.

### 3.7 Boss Summoning & Slaying

**Legitimate summoning** (no spawn egg): hold the Soul Bottle + **Wither Rose**, throw it in the corresponding biome to cast (INCURRENCE, 9 seconds):

| Biome | Boss |
| ---- | ---- |
| Night Wastes | **Serpent** |
| Desert of Rime | **Sorcerer** |
| Infernal Chasms | **Emissary** (pulls the player into a dedicated dimension + forces Adventure mode) |

If the biome does not match, only a "cannot incur" message is shown. Kills drop the corresponding domain artifact (1 each) + materials (nightmare scale / seelic essence, etc.).

**Boss overview**:
- **Serpent**: multi-segmented body (multipart hitbox), ice spike waves / beam hazard zones.
- **Sorcerer**: timeline-based multi-phase, ice spikes / ice blades / beam nodes / dash / shard hailstorm.
- **Emissary**: six attacks — sword plunge (stone trail + red-dust warning circle + 105t three-stage shockwave ring + fire field), flame pillars, wing blast (red-dust warning line), flame slash, flame geysers, apocalypse rain; full wing hitbox.

### 3.8 Ordinary Mobs

- **Nightmare Apparition**: spawns in night_wastes via phantom replacement (cap 30). Floats and drifts; becomes alert when the player **walks or sprints** (sneaking does not alert); red dust in both eyes while chasing; dissipates in black fog (drops see 3.4).
- **Death Walker**: spawns in cimmerian_gorge via illusioner replacement (cap 3). Wanders (auto-stops at cliff edges), slam stomp explosion (30-block sound).

### 3.9 Tony Event

Stay at light level 0 inside the **Dream Crypt** structure; after a cooldown of 10-20 minutes, a black cat **"Tony"** spawns randomly (obfuscated name with purple styling + black collar). It looks at you for 20-40 seconds then disappears, dropping **dreams (Book of Dreams)** — a LoreItem; right-click to read chapter by chapter (re-reading advances progress, capped at the chapter count).

### 3.10 Structures & Loot

| Structure | Contents |
| ---- | ---- |
| Slatehenge | artifact chest (1 random of 13) + miscellaneous |
| Monolith | catechism + 30% chance artifact |
| Dream Crypt | Tony trigger + progression |
| Outpost | ancient_journals |
| Nightmare Fossil | ambient structure |

***

## 4. Commands (permission level 2)

| Command | Effect |
| ---- | ---- |
| `/nyctocosm summon serpent\|sorcerer\|emissary\|death_walker\|nightmare_apparition` | Spawn 8 blocks ahead (ordinary mobs can stack; Emissary is unique across dimensions) |
| `/nyctocosm travel nyctocosm\|dusk_plane\|emissary_domain` | Teleport to the specified dimension |
| `/nyctocosm return` | Return to the Overworld from a sub-dimension |
| `/nyctocosm give <item> [count]` | Grant any registered item (Tab completion) |

## 5. Creative Tabs & JEI

- Creative inventory is split into 5 categories: **Weapons & Foci / Artifacts / Materials / Spawn Eggs / World & Rituals**.
- With JEI installed: search item names to look up **altar recipes** (dual input + single output, with quantities); JEI has zero effect when not installed.

## 6. Localization

- Bilingual: `en_us.json` / `zh_cn.json`. Biomes (Night Wastes / Desert of Rime / Infernal Chasms / Cimmerian Gorge / Dusktowns / Dark Mire / Twilight Grove / Emissary Domain) and structures (Dream Crypt / Slatehenge / Nightmare Fossil / Monolith / Outpost) are all localized.

***

## 7. Project Structure (Package Layout)

```
com.yifei.nyctocosm/
├── NyctocosmMod / Content / CommonEvents   # entry, registry hub, global events
├── spell/       # SeelicField, SpellBolt/SpellArea, Reflectance, HypertraceService, etc.
├── combat/      # Combat six-element damage
├── boss/        # Boss facade + BossEntity base + sorcerer/serpent/emissary controllers + OrdinaryController
├── item/        # WeaponItem, FocusItem, ArtifactItem, LoreItem
├── world/       # portal/ (PortalBlock etc.), structure/
├── dais/        # DaisBlock/DaisMenu/DaisRitual
├── entity/      # SoulEntity, TonyManager
├── network/     # Network
├── migration/   # LegacyMigration (legacy datapack save migration)
├── client/      # rendering/sounds/GUI + compat/ (JEI)
└── gametest/    # GameTests series (excluded from release JAR)
```

Animation uses the native_rigs sampled-matrix approach (`assets/nyctocosm/native_rigs/*.json.gz`, offline AJ per-frame matrix export + runtime TRS interpolation); artifacts use pure Java inventory detection (no external dependency).
