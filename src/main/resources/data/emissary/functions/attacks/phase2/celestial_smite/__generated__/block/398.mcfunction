#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function emissary:attacks/phase2/celestial_smite/__generated__/block/399
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:damage_radiance/apply
scoreboard players operation @s m.damage_taken.radiance += damageAmt value
execute as @e[tag=emissary,tag=emissarymodel,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function emissary:attacks/phase2/celestial_smite/__generated__/block/400
scoreboard players reset IsPlayer value