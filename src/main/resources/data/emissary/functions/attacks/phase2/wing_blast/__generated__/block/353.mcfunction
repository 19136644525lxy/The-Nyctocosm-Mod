#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches -4..0 run function emissary:attacks/phase2/wing_blast/__generated__/block/354
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 0..4 run function emissary:attacks/phase2/wing_blast/__generated__/block/355
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 4..8 run function emissary:attacks/phase2/wing_blast/__generated__/block/356
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 8..12 run function emissary:attacks/phase2/wing_blast/__generated__/block/357
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 12..16 run function emissary:attacks/phase2/wing_blast/__generated__/block/358
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 16..20 run function emissary:attacks/phase2/wing_blast/__generated__/block/359
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 20..24 run function emissary:attacks/phase2/wing_blast/__generated__/block/360
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 24..28 run function emissary:attacks/phase2/wing_blast/__generated__/block/361
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 28..32 run function emissary:attacks/phase2/wing_blast/__generated__/block/362
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 32..36 run function emissary:attacks/phase2/wing_blast/__generated__/block/363
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 36..40 run function emissary:attacks/phase2/wing_blast/__generated__/block/364
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 40..44 run function emissary:attacks/phase2/wing_blast/__generated__/block/365
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 44..48 run function emissary:attacks/phase2/wing_blast/__generated__/block/366
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 48..52 run function emissary:attacks/phase2/wing_blast/__generated__/block/367
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 52..56 run function emissary:attacks/phase2/wing_blast/__generated__/block/368
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 56..60 run function emissary:attacks/phase2/wing_blast/__generated__/block/369
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 60..64 run function emissary:attacks/phase2/wing_blast/__generated__/block/370
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 64..68 run function emissary:attacks/phase2/wing_blast/__generated__/block/371
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 68..72 run function emissary:attacks/phase2/wing_blast/__generated__/block/372
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 72..76 run function emissary:attacks/phase2/wing_blast/__generated__/block/373
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 76..80 run function emissary:attacks/phase2/wing_blast/__generated__/block/374
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 80..84 run function emissary:attacks/phase2/wing_blast/__generated__/block/375
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 84..88 run function emissary:attacks/phase2/wing_blast/__generated__/block/376
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 88..92 run function emissary:attacks/phase2/wing_blast/__generated__/block/377
scoreboard players operation TEMP em.value = RANGE em.value
scoreboard players operation TEMP em.value += @s em.timer
execute if score TEMP em.value matches 92..96 run function emissary:attacks/phase2/wing_blast/__generated__/block/378
scoreboard players reset TEMP em.value
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function emissary:attacks/phase2/wing_blast/__generated__/block/379
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase2/wing_blast/__generated__/block/380
execute as @s[tag=!reflected] positioned ^ ^ ^1 run function emissary:attacks/phase2/wing_blast/raycast
execute as @s[tag=reflected] rotated as @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^1 run function emissary:attacks/phase2/wing_blast/raycast