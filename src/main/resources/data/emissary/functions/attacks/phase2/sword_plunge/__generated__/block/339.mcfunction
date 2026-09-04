#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function emissary:attacks/phase2/sword_plunge/__generated__/block/340
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:damage_generic/apply
scoreboard players operation @s m.damage_taken.generic += damageAmt value
execute as @e[tag=emissary,tag=attack,tag=sword_plunge,sort=nearest,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function emissary:attacks/phase2/sword_plunge/__generated__/block/341
scoreboard players reset IsPlayer value