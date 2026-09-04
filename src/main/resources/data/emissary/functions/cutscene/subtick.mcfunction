#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.cutscenetimer 1
effect give @s slowness 1 5 true
effect give @s jump_boost 1 128 true
execute if score @s em.cutscenetimer matches 150 run function emissary:cutscene/__generated__/block/35
execute if score @s em.cutscenetimer matches 230 run function emissary:cutscene/__generated__/block/36
execute if score @s em.cutscenetimer matches 310 run function emissary:cutscene/__generated__/block/37
execute if score @s em.cutscenetimer matches 390 run function emissary:cutscene/__generated__/block/38
execute if score @s em.cutscenetimer matches 470 run function emissary:cutscene/__generated__/block/39
execute if score @s em.cutscenetimer matches 550 run function emissary:cutscene/__generated__/block/40
execute if score @s em.cutscenetimer matches 630 run function emissary:cutscene/__generated__/block/41
execute if score @s em.cutscenetimer matches 710 run function emissary:cutscene/__generated__/block/42
execute if score @s em.cutscenetimer matches 790 run function emissary:cutscene/__generated__/block/43
execute at @s if score @s em.cutscenetimer matches 840 run function emissary:cutscene/__generated__/block/44
execute if score @s em.cutscenetimer matches 1000 run function emissary:cutscene/__generated__/block/45
execute if score @s em.cutscenetimer matches 1080 run function emissary:cutscene/__generated__/block/46
execute if score @s em.cutscenetimer matches 1160 run function emissary:cutscene/__generated__/block/47
execute if score @s em.cutscenetimer matches 1300 run function emissary:cutscene/__generated__/block/48