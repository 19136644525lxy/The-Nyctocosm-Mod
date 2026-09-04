#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function spells:__generated__/block/80
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:api/voiddamage
scoreboard players operation @s m.damage_taken.void += damageAmt value
execute as @a[tag=damagetemp,limit=1] if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function spells:__generated__/block/81
scoreboard players reset IsPlayer value