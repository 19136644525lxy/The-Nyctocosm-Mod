#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add matchedid
scoreboard players operation Matcher id = @s id
execute as @e if score @s id = Matcher id run tag @s add matchedid