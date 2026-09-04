#built using mc-build (https://github.com/mc-build/mc-build)

tag @s[predicate=!artifacts:is_sneaking] remove a.holdingsneak
scoreboard players remove @s[tag=!a.dash,scores={a.dashcooldown=1..}] a.dashcooldown 1
execute as @s[tag=a.dash] run function artifacts:locomotion/__generated__/block/38