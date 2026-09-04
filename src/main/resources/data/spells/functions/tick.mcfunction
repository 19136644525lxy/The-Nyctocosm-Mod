#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[tag=s.immoveable] run tag @s remove s.immoveable
execute as @e[tag=spell] at @s run function spells:__generated__/block/0
execute as @e[tag=spellcircle] at @s run function spells:__generated__/block/158
execute as @e[tag=s.submerging] at @s run function spells:__generated__/block/165
execute as @a at @s run function spells:playertick