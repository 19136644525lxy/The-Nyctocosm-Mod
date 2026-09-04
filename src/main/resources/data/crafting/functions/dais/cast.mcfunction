#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s rotated ~ 0 positioned ^ ^ ^1 align xyz positioned ~0.5 ~ ~0.5 run function crafting:dais/scan
execute if score DaisScan value matches 1 at @s rotated ~ 0 positioned ^ ^1 ^1 unless entity @e[tag=dais,tag=root,distance=..3] align xyz positioned ~0.5 ~ ~0.5 run function crafting:dais/construct
scoreboard players reset DaisScan value