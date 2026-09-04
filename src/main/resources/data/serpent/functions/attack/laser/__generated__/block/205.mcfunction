#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add lasering
function effects:nightmare_vein/explode
tp @e[tag=target,tag=serpent] @a[tag=serpenttarget,limit=1]
execute as @e[tag=target,tag=serpent] at @s facing entity @e[tag=serpent,tag=head] feet rotated ~ 0 run tp @s ^ ^ ^20 ~180 ~
scoreboard players set SerpentSpeed value 1
scoreboard players set SerpentInterp value 1