#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = flameslashdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase3/flame_slash/__generated__/block/547
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s anchored eyes facing entity @a[distance=..300,limit=1,gamemode=!spectator,tag=!emissarydebug,sort=nearest] eyes positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^2.5 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^0.8
particle lava ~ ~ ~ 0.2 0.2 0.2 0 3 force
particle wax_on ~ ~ ~ 0.2 0.2 0.2 0.05 5 force
particle flame ^ ^2 ^0 0 0 0 0.05 1 force
particle smoke ^ ^2 ^0 0 0 0 0.05 1 force
execute positioned ^ ^2 ^0 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/548
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/549
particle flame ^ ^1.0806046117362795 ^1.682941969615793 0 0 0 0.05 1 force
particle smoke ^ ^1.0806046117362795 ^1.682941969615793 0 0 0 0.05 1 force
execute positioned ^ ^1.0806046117362795 ^1.682941969615793 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/550
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/551
particle flame ^ ^-0.8322936730942848 ^1.8185948536513634 0 0 0 0.05 1 force
particle smoke ^ ^-0.8322936730942848 ^1.8185948536513634 0 0 0 0.05 1 force
execute positioned ^ ^-0.8322936730942848 ^1.8185948536513634 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/552
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/553
particle flame ^ ^-1.9799849932008908 ^0.2822400161197344 0 0 0 0.05 1 force
particle smoke ^ ^-1.9799849932008908 ^0.2822400161197344 0 0 0 0.05 1 force
execute positioned ^ ^-1.9799849932008908 ^0.2822400161197344 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/554
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/555
particle flame ^ ^-1.3072872417272239 ^-1.5136049906158564 0 0 0 0.05 1 force
particle smoke ^ ^-1.3072872417272239 ^-1.5136049906158564 0 0 0 0.05 1 force
execute positioned ^ ^-1.3072872417272239 ^-1.5136049906158564 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/556
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/557
particle flame ^ ^0.5673243709264525 ^-1.917848549326277 0 0 0 0.05 1 force
particle smoke ^ ^0.5673243709264525 ^-1.917848549326277 0 0 0 0.05 1 force
execute positioned ^ ^0.5673243709264525 ^-1.917848549326277 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/558
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/559
particle flame ^ ^1.9203405733007322 ^-0.5588309963978517 0 0 0 0.05 1 force
particle smoke ^ ^1.9203405733007322 ^-0.5588309963978517 0 0 0 0.05 1 force
execute positioned ^ ^1.9203405733007322 ^-0.5588309963978517 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/560
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/561
particle flame ^ ^1.5078045086866092 ^1.3139731974375781 0 0 0 0.05 1 force
particle smoke ^ ^1.5078045086866092 ^1.3139731974375781 0 0 0 0.05 1 force
execute positioned ^ ^1.5078045086866092 ^1.3139731974375781 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/562
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/563
particle flame ^ ^-0.2910000676172271 ^1.9787164932467636 0 0 0 0.05 1 force
particle smoke ^ ^-0.2910000676172271 ^1.9787164932467636 0 0 0 0.05 1 force
execute positioned ^ ^-0.2910000676172271 ^1.9787164932467636 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/564
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/565
particle flame ^ ^-1.8222605237693539 ^0.8242369704835132 0 0 0 0.05 1 force
particle smoke ^ ^-1.8222605237693539 ^0.8242369704835132 0 0 0 0.05 1 force
execute positioned ^ ^-1.8222605237693539 ^0.8242369704835132 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/566
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/567
particle flame ^ ^-1.6781430581529049 ^-1.0880422217787395 0 0 0 0.05 1 force
particle smoke ^ ^-1.6781430581529049 ^-1.0880422217787395 0 0 0 0.05 1 force
execute positioned ^ ^-1.6781430581529049 ^-1.0880422217787395 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/568
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/569
particle flame ^ ^0.00885139597610157 ^-1.999980413101407 0 0 0 0.05 1 force
particle smoke ^ ^0.00885139597610157 ^-1.999980413101407 0 0 0 0.05 1 force
execute positioned ^ ^0.00885139597610157 ^-1.999980413101407 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/570
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/571
particle flame ^ ^1.6877079174649843 ^-1.0731458360008699 0 0 0 0.05 1 force
particle smoke ^ ^1.6877079174649843 ^-1.0731458360008699 0 0 0 0.05 1 force
execute positioned ^ ^1.6877079174649843 ^-1.0731458360008699 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/572
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/573
particle flame ^ ^1.8148935629003924 ^0.8403340736532818 0 0 0 0.05 1 force
particle smoke ^ ^1.8148935629003924 ^0.8403340736532818 0 0 0 0.05 1 force
execute positioned ^ ^1.8148935629003924 ^0.8403340736532818 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/574
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/575
particle flame ^ ^0.2734744364156672 ^1.9812147113897407 0 0 0 0.05 1 force
particle smoke ^ ^0.2734744364156672 ^1.9812147113897407 0 0 0 0.05 1 force
execute positioned ^ ^0.2734744364156672 ^1.9812147113897407 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/576
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/577
particle flame ^ ^-1.5193758257176426 ^1.3005756803142337 0 0 0 0.05 1 force
particle smoke ^ ^-1.5193758257176426 ^1.3005756803142337 0 0 0 0.05 1 force
execute positioned ^ ^-1.5193758257176426 ^1.3005756803142337 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/578
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/579
particle flame ^ ^-1.9153189606467693 ^-0.5758066333301306 0 0 0 0.05 1 force
particle smoke ^ ^-1.9153189606467693 ^-0.5758066333301306 0 0 0 0.05 1 force
execute positioned ^ ^-1.9153189606467693 ^-0.5758066333301306 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/580
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/581
particle flame ^ ^-0.5503266761031939 ^-1.9227949837591136 0 0 0 0.05 1 force
particle smoke ^ ^-0.5503266761031939 ^-1.9227949837591136 0 0 0 0.05 1 force
execute positioned ^ ^-0.5503266761031939 ^-1.9227949837591136 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/582
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/583
particle flame ^ ^1.3206334164881603 ^-1.5019744935433523 0 0 0 0.05 1 force
particle smoke ^ ^1.3206334164881603 ^-1.5019744935433523 0 0 0 0.05 1 force
execute positioned ^ ^1.3206334164881603 ^-1.5019744935433523 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/584
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/585
particle flame ^ ^1.9774092363733384 ^0.2997544193259047 0 0 0 0.05 1 force
particle smoke ^ ^1.9774092363733384 ^0.2997544193259047 0 0 0 0.05 1 force
execute positioned ^ ^1.9774092363733384 ^0.2997544193259047 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] positioned ~-0.99 ~-0.99 ~-0.99 as @s[dx=0] run function emissary:attacks/phase3/flame_slash/__generated__/block/586
execute if entity @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/587
execute if score @s em.value matches 40.. run tag @s add end
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase3/flame_slash/__generated__/block/588