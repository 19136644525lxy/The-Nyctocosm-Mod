#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove in_plane
execute at @s if entity @e[tag=spell,tag=plane,tag=origin,distance=..3] run tag @s add in_plane
execute as @s[tag=in_plane,x_rotation=88..90,predicate=nyctocosm:is_sneaking,tag=!cancelsnuck] at @s run function spells:__generated__/block/177
tag @s[tag=cancelsnuck,predicate=!nyctocosm:is_sneaking] remove cancelsnuck
execute as @s[scores={click_ring=1..},predicate=spells:ring/wearing_ring,predicate=nyctocosm:is_sneaking] at @s run function spells:__generated__/block/178
execute as @s[tag=luminous] at @s run function spells:__generated__/block/182
execute as @s[tag=s.submerging] at @s run function spells:__generated__/block/184
execute as @s[tag=resistant] at @s run function spells:__generated__/block/186
execute as @s[scores={s.transmittance_timer=1..}] at @s run function spells:__generated__/block/188
scoreboard players reset @s click_bottle
scoreboard players reset @s click_ring
scoreboard players reset @s submergencedeath
execute as @a run function spells:__generated__/block/190