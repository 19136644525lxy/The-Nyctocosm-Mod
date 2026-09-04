#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation 1 temp = @s m.spell.power
scoreboard players operation 1 temp *= #4 const
scoreboard players operation 0 temp = Subsistence s.damage
scoreboard players operation 0 temp += 1 temp
scoreboard players operation 0 temp *= #1000 const
scoreboard players operation @s ncd_heal = 0 temp
function ncdamage:apply_changes
effect give @s saturation 3 0 true
particle poof ~ ~1 ~ 0.2 0.2 0.2 0.2 50 force
execute at @s rotated ~ -90 run function effects:elements/radiance/transmutate/play