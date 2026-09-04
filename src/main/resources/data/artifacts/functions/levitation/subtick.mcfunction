#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[predicate=!artifacts:is_sneaking] run tag @s remove a.levitation.holdingsneak
execute as @s[predicate=artifacts:is_sneaking,tag=!a.levitation.holdingsneak,scores={m.hypertrace.charge=3..}] at @s if block ~ ~-1 ~ air if block ~ ~-2 ~ air run function artifacts:levitation/activate